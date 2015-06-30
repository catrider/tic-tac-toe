(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer :all]))

(deftest parse-loc-test
  (testing "it works"
    (is (= (parse-loc "1,2") [1,2]))
    (is (= (parse-loc "12,34") [12,34]))
    (is (= (parse-loc " 0 , 2 ") [0,2]))))

(deftest random-piece-test
  (testing "it works"
    (let [piece (random-piece)]
      (is (or (= :x piece) (= :o piece))))))
