; == Variables ==
(define left_offset 20)

(define bottom_offset 29)

(define real_ratio 32)


; == Universal methods ==
(define (join_lists lst1 lst2)
    (cond ((null? lst1)
           lst2)
          ((null? lst2)
           lst1)
          (else
              (cons (car lst1)
                    (join_lists (cdr lst1) lst2)))))

; == LINE method ==
(define y_value
    (lambda (start_x start_y end_x end_y x)
        (round (+ (* (/ (- end_y start_y) (- end_x start_x)) (- x start_x)) start_y))))

(define x_axis_values
    (lambda (start_x end_x)
        (if (> start_x end_x)
            '()
            (cons start_x (x_axis_values (+ start_x 1) end_x)))))

(define y_axis_values
    (lambda (start_x start_y end_x end_y x_values pair)
        (if (null? x_values)
            pair
            (cons (cons (car x_values) (y_value start_x start_y end_x end_y (car x_values)))
                  (y_axis_values start_x start_y end_x end_y (cdr x_values) pair)))))

;(define LINE
;    (lambda (start_x start_y end_x end_y)
;        (y_axis_values
;            (+ start_x left_offset)
;            (+ (* start_y real_ratio) bottom_offset)
;            (+ (* end_x real_ratio) left_offset)
;            (+ bottom_offset (* end_y real_ratio))
;            (x_axis_values (+ start_x left_offset)
;                    (+ (* end_x real_ratio) left_offset)) '()))) ; why we are passing empty list?

; == LINE method ==
(define LINE
    (lambda (start_x start_y end_x end_y)
        (y_axis_values
            (+ start_x left_offset)
            (+ start_y bottom_offset)
            (+ end_x left_offset)
            (+ end_y bottom_offset)
            (x_axis_values
                (+ start_x left_offset)
                (+ end_x left_offset)) '())))

(define v_line
    (lambda (start_x start_y end_y)
        (if (> start_y end_y)
            '()
            (cons (cons start_x start_y) (v_line start_x (+ start_y 1) end_y)))))

; == VERTICAL LINE method ==
(define VERTICAL_LINE
    (lambda (start_x start_y end_x end_y)
        (v_line
            (+ start_x left_offset)
            (+ start_y bottom_offset)
            (+ end_y bottom_offset))))

; == RECTANGLE method ==
(define RECTANGLE
    (lambda (x1 y1 x2 y2)
        (let (
              (vertical_left (VERTICAL_LINE x1 y1 x1 y2))
              (vertical_right (VERTICAL_LINE x2 y1 x2 y2))
              (horizontal_bottom (LINE x1 y1 x2 y1))
              (horizontal_top (LINE x1 y2 x2 y2))
             )
            (let (
                     (vertical_lines (join_lists vertical_left vertical_right))
                     (horizontal_lines (join_lists horizontal_bottom horizontal_top))
                 )
                (join_lists vertical_lines horizontal_lines))
            )))

(define TEXT-AT
    (lambda (x y text)
        (cons (cons x y) text)))