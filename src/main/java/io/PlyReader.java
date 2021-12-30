package io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import errors.EndHeaderException;
import errors.NoAsciiFileException;
import errors.NoFaceException;
import errors.NoVertexEception;
import maths.Point;
import maths.Polygon;
import maths.Repere;
import views.MainController;

public class PlyReader {

    /**
     * Pour régler le problème
     * de use utility classe
     */
    private PlyReader() {
    }

    /**
     * Methode pour lire les fichier ply
     *
     * @param fileName
     * @return
     * @throws IOException
     * @throws NoAsciiFileException
     */
    public static Repere readFile(String fileName) throws IOException, NoAsciiFileException, NoVertexEception, NoFaceException, EndHeaderException {
        long befTime = 0;
        befTime = System.nanoTime();
        List<String> lines = getLines(fileName);

        int cpt = 0;

        String line;

        int nbPoints = 0;

        boolean hasColor = false;

        String name = "";
        name = new File(fileName).getName();
        String description = "";
        String auteur = "";
        LocalDate date = null;
        String str = "";

        do {
            line = lines.get(cpt);
            line = line.trim();
            if (line != null && !line.equals("")) {
                String[] options = line.split(" ");
                switch (options[0]) {
                    case "description:":
                        options = Arrays.copyOfRange(options, 1, options.length);
                        description = String.join(" ", options);
                        break;
                    case "auteur:":
                        options = Arrays.copyOfRange(options, 1, options.length);
                        auteur = String.join(" ", options);
                        break;
                    case "dateCreation:":
                        date = LocalDate.parse(options[1]);
                        break;
                    case "property":
                        str = options[2];
                        if (str.equals("red") || str.equals("green") || str.equals("blue"))
                            hasColor = true;
                        break;
                    case "format":
                        if (!options[1].equalsIgnoreCase("ascii")) {
                            throw new NoAsciiFileException("Le fichier choisi n'est pas en ASCII");
                        }
                        break;
                    case "element":
                        if (options[1].equals("vertex")) nbPoints = Integer.parseInt(options[2]);
                        /*if (!options[1].equals("vertex")) {
                            throw new NoVertexEception();
                        }*/

                        /*if (!options[1].equals("face")) {
                            throw new NoFaceException();
                        }*/
                    break;
                    /*case "end_header":
                        throw new EndHeaderException();*/
                    default:
                        break;
                }
            }
            cpt++;
        } while (!line.equals("end_header"));

        Repere repere = Repere.getInstance(1, 1, 1);

        setUpRepere(name, description, auteur, date, repere);

        int pointIdx = 0;

        List<Point> points = new ArrayList<>();

        Point point = null;
        int red = 0;
        int green = 0;
        int blue = 0;
        double xAxis = 0.0;
        double yAxis = 0.0;
        double zAxis = 0.0;

        while (pointIdx < nbPoints && cpt < lines.size()) {
            line = lines.get(cpt).trim();
            if (line != null && !line.equals("")) {
                double[] options = Arrays.stream(lines.get(cpt).trim().split(" "))
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                xAxis = options[0];
                yAxis = options[1];
                zAxis = options[2];
                if (hasColor) {
                    red = (int) options[3];
                    green = (int) options[4];
                    blue = (int) options[5];
                }
                point = new Point(xAxis, yAxis, zAxis, red, green, blue);
                points.add(point);
                pointIdx++;
            }
            cpt++;
        }

        repere.addPoints(points);

        while (cpt < lines.size()) {
            line = lines.get(cpt).trim();
            if (line != null && !line.equals("")) {
                double[] pointsIdx = Arrays.stream(lines.get(cpt).trim().split(" "))
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                Polygon polygon = new Polygon(repere);
                for (int j = 1; j < pointsIdx.length; j++) {
                    points.get((int) pointsIdx[j]).addPolygon(polygon);
                    polygon.addPoint((int) pointsIdx[j]);
                }
                polygon.initializeColor();
                repere.addPolygon(polygon);
            }
            cpt++;
        }

        long aftTime = System.nanoTime();
        if (MainController.cons != null) // Pour faire passer les tests
            MainController.cons.println("Récupération du fichier " + fileName + " réalisé en " + (aftTime - befTime) / 1000000 + " ms.");
        return repere;
    }

    /**
     * On met en place notre repère
     *
     * @param name
     * @param description
     * @param auteur
     * @param date
     * @param repere
     */
    private static void setUpRepere(String name, String description, String auteur, LocalDate date, Repere repere) {
        repere.setName(name);
        repere.setDescription(description);
        repere.setDate(date);
        repere.setAuteur(auteur);
    }

    /**
     * On récupère les lignes des fichiers
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    private static List<String> getLines(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        List<String> lines = Files.readAllLines(path);
        return lines;
    }
}