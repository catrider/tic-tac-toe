(ns tic-tac-toe.logic-utils)

(defn or= [val & args]
  (reduce #(or %1 %2) (map (partial = val) args)))
