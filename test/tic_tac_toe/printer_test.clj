(ns tic-tac-toe.printer-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.printer :refer :all]))

(deftest print-board-test
  (testing "prints the board"
    (let [board [[:o :x :e]
                 [:e :o :e]
                 [:x :o :x]]]
      (is (= (print-board board) (str " o | x |   " \newline
                                "---|---|---" \newline
                                "   | o |   " \newline
                                "---|---|---" \newline
                                " x | o | x " \newline)))))
  (testing "throws an AssertionError if the vector isn't 3x3"
    (let [board [[:o :x :e]
                 [:e :o :e]]]
      (is (thrown? AssertionError (print-board board))))))

(deftest row-to-str-test
  (testing "returns the correct row str"
    (let [row [:o :x :e]]
      (is (= (row-to-str row) " o | x |   \n")))))

(deftest piece-to-char-test
  (testing "it works"
    (is (= (piece-to-char :x) \x))
    (is (= (piece-to-char :o) \o))
    (is (= (piece-to-char :e) \space))))

