(import "jscheme.JScheme")
(load "elf/util.scm")
(define js (JScheme.forCurrentEvaluator))

; Helper functions ---------------------------
(define (firstElement list)
        (car list))

(define (lastElement list)
    (cond ((null? (cdr list)) (car list))
          (else (lastElement (cdr list)))))
; ---------------------------------------------

(define demoTest
    (assert (equal? (+ 2 3) (.eval js '(+ 2 3)))))

; Line start end point tests
(define lineResults (LINE 0 0 10 10))
(assert (equal? (firstElement lineResults) (cons 0 0)))
(assert (equal? (lastElement lineResults) (cons 10 10)))


