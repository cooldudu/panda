package com.wms.ui.controller;

import com.wms.core.utils.common.ResourceBundleUtil;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import com.wms.core.utils.upload.CloseCondition;

@RestController
@RequestMapping(value="upload")
public class UploadController {
    @RequestMapping(method = RequestMethod.POST, value = "logo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> uploadLogoHandler(@RequestBody Flux<Part> parts) {
        return parts
                .filter(part -> part instanceof FilePart)
                .ofType(FilePart.class)
                .flatMap(this::saveFile);
    }

    private Mono<String> saveFile(FilePart filePart) {
        final var filename = filePart.filename();
        var path = ResourceBundleUtil.getString("application","logo.pic.path");
        var filePath = new File(new File("").getAbsolutePath()+path);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        var file = new File(filePath.getPath()+"/"+filename);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            return Mono.error(e);
        }
        try {
            final var fileChannel = AsynchronousFileChannel.open(file.toPath(), StandardOpenOption.WRITE);
            final var closeCondition = new CloseCondition();
            var fileWriteOffset = new AtomicInteger(0);
            var errorFlag = new AtomicBoolean(false);
            return filePart.content().doOnEach(dataBufferSignal -> {
                if (dataBufferSignal.hasValue() && !errorFlag.get()) {
                    var dataBuffer = dataBufferSignal.get();
                    var count = dataBuffer.readableByteCount();
                    var bytes = new byte[count];
                    dataBuffer.read(bytes);
                    final var byteBuffer = ByteBuffer.allocate(count);
                    byteBuffer.put(bytes);
                    byteBuffer.flip();
                    final var filePartOffset = fileWriteOffset.getAndAdd(count);
                    // write the buffer to disk
                    closeCondition.onTaskSubmitted();
                    fileChannel.write(byteBuffer, filePartOffset, null, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            // file part successfuly written to disk, clean up
                            byteBuffer.clear();

                            if (closeCondition.onTaskCompleted()) {
                                try {
                                    fileChannel.close();
                                } catch (IOException ignored) {
                                    ignored.printStackTrace();
                                }
                            }
                        }
                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            errorFlag.set(true);
                        }
                    });
                }
            }).doOnComplete(() -> {
                if (closeCondition.canCloseOnComplete()) {
                    try {
                        fileChannel.close();
                    } catch (IOException ignored) {
                    }
                }

            }).doOnError(t -> {
                try {
                    fileChannel.close();
                } catch (IOException ignored) {
                }
            }).last().map(dataBuffer -> path.replaceAll("/src/main/resources/static/","/console/")
                    + filePart.filename() + ":" + (errorFlag.get() ? "error" : "uploaded") +":"+filePart.name());
        } catch (IOException e) {
            return Mono.error(e);
        }
    }
}
