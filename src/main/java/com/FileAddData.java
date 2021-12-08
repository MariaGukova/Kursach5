package com;

import com.example.it.Project;

import java.io.FileWriter;
import java.io.IOException;

public class FileAddData {
        public static FileAddData fileAddData = new FileAddData();
        private FileAddData(){}

        public void addInFile(Project project) throws IOException {


            FileWriter writer = new FileWriter(project.getName()+".txt", false);
            writer.write(project.toString());
            writer.append('\n');
            writer.flush();

        }
    }

