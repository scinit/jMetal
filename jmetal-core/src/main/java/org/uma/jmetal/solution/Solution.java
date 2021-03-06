package org.uma.jmetal.solution;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Interface representing a Solution
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * @param <T> Type (Double, Integer, etc.)
 */
public interface Solution<T> extends Serializable {
  List<T> variables() ;
  double[] objectives() ;
  double[] constraints() ;
  Map<Object,Object> attributes() ;

  Solution<T> copy() ;

  @Deprecated
  void setObjective(int index, double value) ;
  @Deprecated
  double getObjective(int index) ;
  @Deprecated
  double[] getObjectives() ;

  @Deprecated
  T getVariable(int index) ;
  @Deprecated
  List<T> getVariables() ;
  @Deprecated
  void setVariable(int index, T variable) ;

  @Deprecated
  double[] getConstraints() ;
  @Deprecated
  double getConstraint(int index) ;
  @Deprecated
  void setConstraint(int index, double value) ;

  @Deprecated
  int getNumberOfVariables() ;
  @Deprecated
  int getNumberOfObjectives() ;
  @Deprecated
  int getNumberOfConstraints() ;

  @Deprecated
  void setAttribute(Object id, Object value) ;
  @Deprecated
  Object getAttribute(Object id) ;
  @Deprecated
  boolean hasAttribute(Object id) ;
  @Deprecated
  Map<Object, Object> getAttributes();
}
