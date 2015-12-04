(ns tic-tac-toe.ai
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.piece :as piece]
            [tic-tac-toe.game :as game]
            [tic-tac-toe.logic-utils :as lu]
            [clojure.set]))
4
(defn piece-locations [board piece]
  (->> (flatten board)
       (map-indexed (fn [idx itm]
                      (hash-map :piece itm :x (inc (quot idx 3)) :y (inc (mod idx 3)))))
       (filter #(= (:piece %) piece))
       (map (fn [itm] (vector (:x itm) (:y itm))))
       (set)))

(def all-locations
  (set (for [x (range 1 4)
             y (range 1 4)]
         (vector x y))))

(defn occupied-locations [board]
  (clojure.set/union (piece-locations board :o) (piece-locations board :x)))

(defn winning-move [board piece]
  (->> (occupied-locations board)
       (clojure.set/difference all-locations)
       (filter #(= piece (game/winner (board/insert board (first %) (second %) piece))))
       (first)))

(def corners 
  (for [x [1 3]
        y [1 3]]
    (vector x y)))

(defn random-corner []
  (first (shuffle corners)))

(defn random-remaining-location [board]
  (first (clojure.set/difference all-locations (occupied-locations board))))

(defn is-1-or-3? [val]
  (lu/or= val 1 3))

(defn adjacent? [[x1 y1] [x2 y2]]
  (cond 
   (lu/and= 2 x1 y1) (lu/or-fn is-1-or-3? x2 y2)
   (lu/and= 2 x2 y2) (lu/or-fn is-1-or-3? x1 y1)
   :else (and 
          (lu/and-fn is-1-or-3? x1 x2 y1 y2)
          (or (= x1 x2) (= y1 y2)))))

(defn tangentially-aligned-with-opponent-piece? [board piece [lx ly :as location]]
  (->> (concat (for [x (range 1 4) y (vector ly)] (vector x y))
               (for [x (vector lx) y (range 1 4)] (vector x y)))
       (remove (hash-set location))
       (some (piece-locations board (piece/other piece)))
       (boolean)))

(defn adjacent-to-played-piece? [board piece location]
  (->> (piece-locations board piece)
       (filter (conj (set corners) [2 2]))
       (some #(adjacent? location %))
       (boolean)))

(defn location-playability-score [board, piece]
  (fn [location1, location2] 
    (let [location1-tangentially-aligned? (tangentially-aligned-with-opponent-piece? board piece location1)
          location2-tangentially-aligned? (tangentially-aligned-with-opponent-piece? board piece location2)
          location1-adjacent? (adjacent-to-played-piece? board piece location1)
          location2-adjacent? (adjacent-to-played-piece? board piece location2)]
      (cond
       (and (not location1-tangentially-aligned?) location1-adjacent?) -1
       (and (not location2-tangentially-aligned?) location2-adjacent?) 1
       (not location1-tangentially-aligned?) -1
       (not location2-tangentially-aligned?) -1
       (identity location1-adjacent?) -1
       (identity location2-adjacent?) 11
       :else 0))))

(defn next-move [board piece]
  (let [my-played (piece-locations board piece)
        his-played (piece-locations board (piece/other piece))
        my-winning-move (winning-move board piece)
        his-winning-move (winning-move board (piece/other piece))
        moves (sort (location-playability-score board piece) (clojure.set/difference (set (for [x (range 1 4) y (range 1 4)] (vector x y))) (piece-locations board piece) (piece-locations board (piece/other piece))))]
    (cond
     (not (nil? my-winning-move)) my-winning-move
     (not (nil? his-winning-move)) his-winning-move
     (and (empty? my-played) (empty? his-played)) (random-corner)
     (and (= 1 (count his-played)) (= 0 (count my-played)) (not (board/occupied? board 2 2))) [2 2]
     (not (empty? moves)) (first moves)
     :else (random-remaining-location board))))
