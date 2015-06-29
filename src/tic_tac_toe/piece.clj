(ns tic-tac-toe.piece)

(defn other [piece]
  {:pre [(or (= piece :x) (= piece :o))]}
  (case piece :x :o :o :x))
