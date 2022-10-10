package vdcom;

import vdcom.base.Content;
import vdcom.base.Value;
import vdcom.service.ContentService;
import vdcom.util.CreateParseAndWrite;

import java.io.IOException;
import java.util.*;

public class ApplicationRunner {
    public static void main(String[] args) throws IOException {
        List<Content> fileText = CreateParseAndWrite.parseFileLines("entry.txt");
        Map<String, Value> data = ContentService.getMapFromFields(fileText);
        ContentService content = new ContentService(data);
        List<Content> emptyContent = ContentService.emptyFields(fileText);

        for (Content current : emptyContent) {
            content.update(current);
        }

        CreateParseAndWrite.toResultFile(emptyContent);
    }
}