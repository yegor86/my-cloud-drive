package org.odesamama.mcd;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by starnakin on 17.09.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class HDFSTests {

    @Value("${hdfs.namenode.url}")
    private String nameNodeUrl;

    private String filePath = "/testfileslocation/";

    @Test
    public void writeAndReadFilesToHDFS() throws URISyntaxException, IOException {

        System.setProperty("HADOOP_USER_NAME", "hadoopuser");

        Configuration conf = new Configuration();
        // save file
        String fileName = System.currentTimeMillis() + ".txt";
        Path file = new Path(nameNodeUrl + filePath + fileName);

        try (FileSystem fileSystem = FileSystem.get(new URI(nameNodeUrl), conf)) {
            System.out.println(nameNodeUrl);

            if (fileSystem instanceof DistributedFileSystem) {
                System.out.println("HDFS is the underlying filesystem");
            } else {
                System.out.println("Other type of file system " + fileSystem.getClass());
            }

            OutputStream os = fileSystem.create(file, () -> System.out.println("File written"));
            try (BufferedOutputStream bw = new BufferedOutputStream(os)) {
                bw.write("Test content".getBytes());
            }
        }

        // read file
        try (FileSystem fileSystem = FileSystem.get(new URI(nameNodeUrl), conf)) {

            try (BufferedReader br = new BufferedReader(new InputStreamReader(fileSystem.open(file)))) {
                String line = br.readLine();
                while (line != null) {
                    System.out.println(line);
                    line = br.readLine();
                }
            }
        }
    }

    @Test
    public void listFiles() throws URISyntaxException, IOException {

        //write at least one file to system
        //writeAndReadFilesToHDFS();

        // System.setProperty("HADOOP_HOME", "/home/hadoopuser/hadoop");
        System.setProperty("HADOOP_USER_NAME", "hadoopuser");

        Configuration conf = new Configuration();

        FileSystem fileSystem = FileSystem.get(new URI(nameNodeUrl), conf);

        RemoteIterator<LocatedFileStatus> fileStatusListIterator = fileSystem
                .listFiles(new Path(nameNodeUrl + filePath), true);
        while (fileStatusListIterator.hasNext()) {
            LocatedFileStatus fileStatus = fileStatusListIterator.next();
            System.out.println(fileStatus.getPath());
        }
    }
}
