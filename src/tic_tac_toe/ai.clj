(ns tic-tac-toe.ai
  (:require [tic-tac-toe.board :as board]
            [clojure.set]))

(defn other-piece [piece]
  {:pre [(or (= piece :x) (= piece :o))]}
  (case piece :x :o :o :x))

(defn piece-locations [board piece]
  (->> (flatten board)
       (map-indexed (fn [idx itm]
                      (hash-map :piece itm :x (inc (quot idx 3)) :y (inc (mod idx 3)))))
       (filter #(= (:piece %) piece))
       (map (fn [itm] (vector (:x itm) (:y itm))))
       (set)))

(defn missing-piece [pieces]
  (if (apply = (map first pieces))
    (vector (ffirst pieces) (first (clojure.set/difference #{1 2 3} (set (map second pieces)))))
    (vector (first (clojure.set/difference #{1 2 3} (set (map first pieces)))) (second (first pieces)))))

(defn winning-move [board piece]
  (let [played (map #(conj % piece) (piece-locations board piece)) 
        played-rows (vals (group-by first played))
        played-columns (vals (group-by second played))
        rows-to-win (filter #(= 2 (count %)) played-rows)
        columns-to-win (filter #(= 2 (count %)) played-columns)]
    (cond
     (> 0 (count rows-to-win)) (missing-piece (first rows-to-win))
     (> 0 (count columns-to-win)) (missing-piece (first columns-to-win))
     :else nil)))

(defn next-move [board piece]
  (let [my-played (piece-locations board piece)

        his-played (piece-locations board (other-piece piece))]
    (cond
     (not= nil (winning-move board piece)) (winning-move board piece) 
     (and (empty? my-played) (empty? his-played)) (->> (for [x [1 3]
                                                             y [1 3]]
                                                         (vector x y))
                                                       (shuffle)
                                                       (first))
     :else [2,2])))
