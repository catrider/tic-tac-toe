(ns tic-tac-toe.ai
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.piece :as piece]
            [tic-tac-toe.game :as game]
            [clojure.set]))

(defn piece-locations [board piece]
  (->> (flatten board)
       (map-indexed (fn [idx itm]
                      (hash-map :piece itm :x (inc (quot idx 3)) :y (inc (mod idx 3)))))
       (filter #(= (:piece %) piece))
       (map (fn [itm] (vector (:x itm) (:y itm))))
       (set)))

(defn winning-move [board piece]
  (->> (clojure.set/union (piece-locations board piece) (piece-locations board (piece/other piece)))
       (clojure.set/difference (set (for [x (range 1 4)
                                          y (range 1 4)]
                                      (vector x y))))
       (filter #(= piece (game/winner? (board/insert board (first %) (second %) piece))))
       (first)))

(defn random-corner []
  (->> (for [x [1 3]
             y [1 3]]
         (vector x y))
       (shuffle)
       (first)))

(defn random-remaining-location [board]
  (first (clojure.set/difference (set (for [x (range 1 4) y (range 1 4)] (vector x y))) (clojure.set/union (piece-locations board :x) (piece-locations board :o)))))

(defn next-move [board piece]
  (let [my-played (piece-locations board piece)
        his-played (piece-locations board (piece/other piece))
        my-winning-move (winning-move board piece)
        his-winning-move (winning-move board (piece/other piece))]
    (cond
     (not (nil? my-winning-move)) my-winning-move
     (not (nil? his-winning-move)) his-winning-move
     (and (empty? my-played) (empty? his-played)) (random-corner)
     (and (= 1 (count his-played)) (= 0 (count my-played)) (not (board/occupied? board 2 2))) [2 2]
     :else (random-remaining-location board))))
