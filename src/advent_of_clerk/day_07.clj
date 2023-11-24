;; # 🎄 Advent of Clerk: Day 7
(ns advent-of-clerk.day-07
  (:require
    [clojure.java.io :as io]
    [clojure.string :as str]
    [nextjournal.clerk :as clerk]))

^::clerk/no-cache
(def input (slurp (io/resource "day-07-sample.txt")))
(def lines (str/split-lines input))
(def structured-lines (map #(str/split % #" ") lines))

(defn update-map [structure line-vec]
  (let [[a0 a1 a2] line-vec]
    (if (= "$" a0)
      (if (= "cd" a1)
        (if (= ".." a2)
          (assoc structure :current-path (pop (vec (:current-path structure))))
          (as-> structure s 
              (assoc s :current-path (conj (vec (:current-path structure)) a2))
              (assoc-in s (:current-path s) 
                        (let [dir-if-present (get-in s (:current-path s))]
                          (if dir-if-present dir-if-present {:type :dir})))))
        ; we do not care about ls
        structure)
      (if (= "dir" a0)
        (assoc-in structure (conj (:current-path structure) a1) {:type :dir})
        (assoc-in structure (conj (:current-path structure) a1) {:type :file, :size (parse-long a0)} )))))

(defn build-up-map [structured-lines] 
  (reduce update-map {} structured-lines))

