(ns hackerrank-string-mingling.core)

(println (apply str (interleave (seq (read-line)) (seq (read-line)))))
