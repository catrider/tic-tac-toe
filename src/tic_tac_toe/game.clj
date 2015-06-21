(ns tic-tac-toe.game)

(defn filled? [row]
  (cond 
   (every? (partial = :x) row) :x
   (every? (partial = :o) row) :o
   :else :undecided))

(defn nth-column [[r1 r2 r3] column]
  (list (nth r1 column) (nth r2 column) (nth r3 column)))

(defn diagonal [[r1 r2 r3] [r1i r2i r3i]]
  (list (nth r1 r1i) (nth r2 r2i) (nth r3 r3i)))

(defn up-diagonal [board]
  (diagonal board (range 2 -1 -1)))

(defn down-diagonal [board]
  (diagonal board (range 0 3 1)))

(defn winner? [[r1 r2 r3 :as board]]
  (let [tests (map filled? (list r1 r2 r3 (nth-column board 0) (nth-column board 1) (nth-column board 2) (up-diagonal board) (down-diagonal board)))]
    (if-let [result (some #(if (or (= :x %) (= :o %)) (identity %) nil) tests)]
            result
            :undecided)))
