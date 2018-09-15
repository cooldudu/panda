package com.wms.ui.controller;

import com.wms.domain.DiagramRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("diagram")
public class DiagramController {
    @GetMapping(value = "getmenu")
    public Mono<String> getMenuDiagram(){
        return new DiagramRepo().getMenuDiagramContent().onErrorReturn("none");
    }
}
