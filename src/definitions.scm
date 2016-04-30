(define y_value
  (lambda (start_x start_y end_x end_y x)
    (+(*(/ (- end_y start_y) (- end_x start_x)) (- x start_x)) start_y)))

(define x_line
  (lambda (start_x end_x)
    (if(= start_x end_x)
       '()
       (cons start_x (x_line (+ start_x 1) end_x)))))

(define y_values
  (lambda (start_x start_y end_x end_y x_values pair)
    (if (null? x_values)
       pair
       (cons (cons (car x_values) (y_value start_x start_y end_x end_y (car x_values))) (y_values start_x start_y end_x end_y (cdr x_values) pair)))))

(define LINE
  (lambda (start_x start_y end_x end_y)
    (y_values start_x start_y end_x end_y (x_line start_x end_x) '())))