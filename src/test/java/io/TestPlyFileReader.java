package io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import errors.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maths.Repere;

/**
 * Test sur la pomme
 */
public class TestPlyFileReader {

    static Repere repere = Repere.getInstance(0, 0, 0);



    @Test
    void test_end_header() {
        IndexOutOfBoundsException exception = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            repere = PlyReader.readFile("plyFilesTest/noEndHeader.ply");
        });

        Assertions.assertEquals("Index 44 out of bounds for length 44", exception.getMessage());
    }

    /*@Test
    void test_no_vertex() {
        NoVertexEception exception = Assertions.assertThrows(NoVertexEception.class, () -> {
            repere = PlyReader.readFile("plyFilesTest/noVertex.ply");
        });

        Assertions.assertEquals("pas de vertex", exception.getMessage());
    }

    @Test
    void test_no_face() {
        NoFaceException exception = Assertions.assertThrows(NoFaceException.class, () -> {
            repere = PlyReader.readFile("plyFilesTest/noFace.ply");
        });

        Assertions.assertEquals("pas de face", exception.getMessage());
    }*/

}
