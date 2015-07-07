(ns tic-tac-toe.logic-utils-test
  (:require [tic-tac-toe.logic-utils :as lu]
            [clojure.test :refer :all]))

(deftest or=-test
  (testing "it works"
    (is (= true (lu/or= 4 1 2 3 4 5)))
    (is (= false (lu/or= 4 5 3 2 6 7 1)))))

(deftest or-fn-test
  (testing "it works"
    (is (= true (lu/or-fn #(< 3 %) 1 2 3 4)))
    (is (= false (lu/or-fn #(< 3 %) -1 0 1 2)))))

(deftest and=-test
  (testing "it works"
    (is (= true (lu/and= 4 4 4 4 4)))
    (is (= false (lu/and= 4 4 4 3 4)))))

(deftest and-fn-test
  (testing "it works"
    (is (= true (lu/and-fn #(< 3 %) 4 5 6 7 8)))
    (is (= false (lu/and-fn #(< 3 %) 2 3 4 5 6)))))
