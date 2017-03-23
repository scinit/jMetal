package org.uma.jmetal.utility;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.archive.impl.NonDominatedSolutionListArchive;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

/**
 * This utility reads a file or the files in a directory and creates a reference front.
 * The file(s) must contain only objective values.
 *
 * The program receives two parameters:
 * 1. the name of the file or directory containing the data
 * 2. the output file name which will contain the generated front
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class GenerateReferenceFrontFromFile {
  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      throw new JMetalException("Wrong number of arguments: two file names are required.");
    }

    String inputFileName = args[0] ;
    String outputFileName = args[1] ;

    File inputFile ;
    inputFile = new File(inputFileName) ;
    if (!inputFile.exists()) {
      throw new JMetalException(inputFileName + " does not exist.");
    }

    NonDominatedSolutionListArchive<?> archive ;

    if (inputFile.isFile()) {
      archive = readDataFromFile(inputFileName) ;

      new SolutionListOutput(archive.getSolutionList())
              .setSeparator("\t")
              .setFunFileOutputContext(new DefaultFileOutputContext(outputFileName))
              .print();
    }

  }

  private static NonDominatedSolutionListArchive<?> readDataFromFile(String inputFileName) throws IOException {
    Stream<String> lines;
    lines = Files.lines(Paths.get(inputFileName), Charset.defaultCharset());

    List<List<Double>> numbers = lines
            .map(line -> {
              String[] textNumbers = line.split(" ") ;
              List<Double> listOfNumbers = new ArrayList<>() ;
              for (String number : textNumbers) {
                listOfNumbers.add(Double.parseDouble(number)) ;
              }

              return listOfNumbers ;
            })
            .collect(toList()) ;

    int numberOfObjectives = numbers.get(0).size() ;
    DummyProblem dummyProblem = new DummyProblem(numberOfObjectives);

    NonDominatedSolutionListArchive<DoubleSolution> archive ;
    archive = new NonDominatedSolutionListArchive<>() ;

    numbers
            .stream()
            .forEach(list -> {
              DoubleSolution solution = new DefaultDoubleSolution(dummyProblem);
              for (int i = 0; i < numberOfObjectives; i++) {
                solution.setObjective(i, list.get(i));
              }
              archive.add(solution) ;
            });


    return archive ;
  }

  private static class DummyProblem extends AbstractDoubleProblem {

    public DummyProblem(int numberOfObjectives) {
      this.setNumberOfObjectives(numberOfObjectives);
    }

    @Override
    public void evaluate(DoubleSolution solution) {

    }
  }
}
