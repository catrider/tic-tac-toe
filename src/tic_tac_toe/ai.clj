(ns tic-tac-toe.ai
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.game :as game]
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

(defn winning-move [board piece]
  (->> (clojure.set/union (piece-locations board piece) (piece-locations board (other-piece piece)))
       (clojure.set/difference (set (for [x (range 1 4)
                                          y (range 1 4)]
                                      (vector x y))))
       (filter #(= piece (game/winner? (board/insert board (first %) (second %) piece))))
       (first)))

(defn next-move [board piece]
  (let [my-played (piece-locations board piece)
        his-played (piece-locations board (other-piece piece))]
    (cond
     (not= nil (winning-move board piece)) (winning-move board piece)
     (not= nil (winning-move board (other-piece piece))) (winning-move board (other-piece piece))
     (and (empty? my-played) (empty? his-played)) (->> (for [x [1 3]
                                                             y [1 3]]
                                                         (vector x y))
                                                       (shuffle)
                                                       (first))
     (not (board/occupied? board 2 2)) [2 2]
     :else (first (clojure.set/difference (set (for [x (range 1 4) y (range 1 4)] (vector x y))) (clojure.set/union (piece-locations board piece) (piece-locations board (other-piece piece))))))))
