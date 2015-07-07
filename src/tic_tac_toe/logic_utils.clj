(ns tic-tac-toe.logic-utils)

(defn or= [val & args]
  (reduce #(or %1 %2) (map (partial = val) args)))

(defn or-fn [fn & args]
  (reduce #(or %1 %2) (map fn args)))

(defn and= [val & args]
  (reduce #(and %1 %2) (map (partial = val) args)))

(defn and-fn [fn & args]
  (reduce #(and %1 %2) (map fn args)))
