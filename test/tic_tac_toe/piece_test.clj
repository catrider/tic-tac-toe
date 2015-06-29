(ns tic-tac-toe.board-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.piece :as piece]))

(deftest other-test
  (testing "it works"
    (is (= (piece/other :x) :o))
    (is (= (piece/other :o) :x)))
  (testing "AssertionError is thrown if input is not :o or :x"
    (is (thrown? AssertionError (piece/other :e)))))
