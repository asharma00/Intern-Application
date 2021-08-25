package com.intern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.intern.service.DatabaseFileService;

@RestController
public class FileDownloadController {

    @Autowired
    private DatabaseFileService fileStorageService;


}