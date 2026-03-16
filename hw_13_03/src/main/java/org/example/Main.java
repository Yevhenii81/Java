package org.example;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class Main {

    static class FileInfo {
        long count = 0;
        long size = 0;
    }

    static Map<String, FileInfo> map = new HashMap<>();

    public static void main(String[] args) throws Exception {

        Path startPath = Paths.get("D:\\source\\repos\\test");

        Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                File f = file.toFile();
                String name = f.getName();
                String ext = "no_ext";

                int dot = name.lastIndexOf(".");
                if (dot != -1 && dot != name.length() - 1) {
                    ext = name.substring(dot + 1).toLowerCase();
                }

                FileInfo info = map.getOrDefault(ext, new FileInfo());
                info.count++;
                info.size += f.length();
                map.put(ext, info);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                return FileVisitResult.CONTINUE;
            }
        });
        List<Map.Entry<String, FileInfo>> list = new ArrayList<>(map.entrySet());
        list.sort((a, b) -> Long.compare(b.getValue().count, a.getValue().count));

        BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"));

        writer.write(String.format("%-4s %-15s %-12s %-12s%n", "№", "расширение", "количество", "объём"));

        int index = 1;

        for (Map.Entry<String, FileInfo> entry : list) {

            if (index > 50) break;
            writer.write(String.format("%-4d %-15s %-12d %-12d%n", index, entry.getKey(), entry.getValue().count, entry.getValue().size));
            index++;
        }
        writer.close();
        System.out.println("Готово. Результат в файле result.txt");
    }
}
