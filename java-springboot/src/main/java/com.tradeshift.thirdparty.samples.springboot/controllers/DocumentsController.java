package com.tradeshift.thirdparty.samples.springboot.controllers;


import com.tradeshift.thirdparty.samples.springboot.domain.dto.BaseDocumentDTO;
import com.tradeshift.thirdparty.samples.springboot.services.DocumentsService;
import com.tradeshift.thirdparty.samples.springboot.services.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentsController {

    static Logger LOGGER = LoggerFactory.getLogger(DocumentsController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    DocumentsService documentsService;

    /**
     * Get documents by document type from remote server by call to rest service
     *
     *
     * @param response
     * @return List of documents by document type from remote server
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public ResponseEntity<?> getDocument(@RequestParam("documentType") final String documentType, final HttpServletResponse response)
            throws ParserConfigurationException, IOException, SAXException {

        LOGGER.info("get list of documents by document type", DemoController.class);

        if (tokenService.getAccessToken() != null) {
            LOGGER.info("get list of documents by document type success", DemoController.class);
            List<BaseDocumentDTO> result = documentsService.getDocuments(documentType);

            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            LOGGER.info("get list of documents by document type filed, access token doesn't exist", DemoController.class);
            response.sendRedirect(tokenService.getAuthorizationCodeURL());

            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

}
