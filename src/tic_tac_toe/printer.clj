(ns tic-tac-toe.printer)

(defn piece-to-char [piece]
  (case piece
    :x \x
    :o \o
    :e \space))

(defn row-to-str [row]
  (apply str 
         (concat " " (->> (map piece-to-char row)
                      (interpose \|)
                      (interpose \space)) " \n")))

(defn print [board]
  {:pre [(and (= 3 (count board)) (every? #(= 3 (count %)) board))]}
  (apply str (interpose (str "---|---|---" \newline) (map row-to-str board))))
