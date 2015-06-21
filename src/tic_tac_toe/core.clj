(ns tic-tac-toe.core
  (:require [tic-tac-toe.game :as game]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.printer :as printer]))


(defn parse-loc [loc]
  (map (comp #(Integer/parseInt %) clojure.string/trim) (clojure.string/split loc #",")))

(defn other-piece [piece]
  {:pre [(or (= piece :x) (= piece :o))]}
  (case piece :x :o :o :x))

(defn start []
  (let [initial-board [[:e :e :e]
                      [:e :e :e]
                      [:e :e :e]]]
    (println "Tic-Tac-Toe!")
    (loop [board initial-board
           piece-up :o
           winner :undecided]
      (do
        (println (printer/print board))
        (println (printer/piece-to-char piece-up) "is up")
        (println "Play your piece: ")
        (let [loc (first (line-seq (java.io.BufferedReader. *in*)))
              [x,y] (parse-loc loc)
              new-board (if (board/occupied? board x y)
                          (do
                            (println (str "Location (" x "," y ") is already occupied."))
                            board)
                          (board/insert board x y piece-up))
              new-piece-up (if (= new-board board)
                             piece-up
                             (other-piece piece-up))
              new-winner (game/winner? new-board)]
          (if (or (= new-winner :x) (= new-winner :o))
            (do
              (println (printer/print new-board))
              (println (printer/piece-to-char piece-up) "wins!"))
            (recur new-board new-piece-up new-winner)))))))
