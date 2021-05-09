package com.xiffox.snippets.pebble;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.StringLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PebbleDemo {
    public static void main(String[] args) {
        PebbleEngine engine = new PebbleEngine.Builder().loader(new StringLoader()).autoEscaping(false).build();
        PebbleTemplate compiledTemplate =
                engine.getTemplate("This is an example of {{exampleName}}");

        Map<String, Object> context = new HashMap<>();
        context.put("exampleName", "pebbleTemplate");

        try {
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, context);
            String output = writer.toString();
            System.out.println(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
