(ns tic-tac-toe.board)

(defn occupied? [board x y]
  {:pre [(and (>= x 1) (<= x 3) (>= y 1) (<= y 3))]}
  (not= :e (get (get board (dec x)) (dec y))))

(defn insert [board, row, column, piece]
  {:pre [(not (occupied? board row column))]}
  (assoc board (dec row) (assoc (get board (dec row)) (dec column) piece)))
