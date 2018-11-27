package org.uma.jmetal.util.aggregativefunction;

import com.sun.tools.jdi.SocketListeningConnector;
import org.uma.jmetal.solution.Solution;

public interface AggregativeFunction<S extends Solution<?>> {
  double compute(S solution, double[] weightVector) ;
}