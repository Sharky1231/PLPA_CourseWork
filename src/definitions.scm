; == Variables ==
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

; == OLDLINE method ==
(define OLDLINE
    (lambda (start_x start_y end_x end_y)
        (y_axis_values
            start_x
            start_y
            end_x
            end_y
            (x_axis_values
                start_x
                end_x) '())))

(define v_line
    (lambda (start_x start_y end_y)
        (if (> start_y end_y)
            '()
            (cons (cons start_x start_y) (v_line start_x (+ start_y 1) end_y)))))

; == VERTICAL LINE method ==
(define VERTICAL_LINE
    (lambda (start_x start_y end_x end_y)
        (v_line
            start_x
            start_y
            end_y)))

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



; == LINE method ==

; FUnction for recursivliy calculating all the points
(define calPoints
  (lambda (x xEnd y yEnd dx dy D)
    (if (= x xEnd) ;Is the end condition reached?
        (cons (cons xEnd yEnd) '()) ;Return end point at end condition
        (if (>= D 0)
            ( cons (cons x y) (calPoints (+ x 1) xEnd (+ y 1) yEnd dx dy (- (+ D dy) dx) )) ;Recursive step
            ( cons (cons x y) (calPoints (+ x 1) xEnd y yEnd dx dy (+ D dy) ))              ;Recursive step
                  ))))

; Function than switch a pair of coordinates from octant 0 to (0-7). From (x,y) to -> see "return comments"
(define switchOctant
  (lambda (octant xyPair)

    (cond ((= octant 0) xyPair)                                        ;return (x, y)
          ((= octant 1) (cons (cdr xyPair) (car xyPair)) )             ;return (y, x)
          ((= octant 2) (cons (- 0 (cdr xyPair)) (car xyPair)) )       ;return (-y, x)
          ((= octant 3) (cons (- 0 (car xyPair)) (cdr xyPair)) )       ;return (-x, y)
          ((= octant 4) (cons (- 0 (car xyPair)) (- 0 (cdr xyPair))) ) ;return (-x, -y)
          ((= octant 5) (cons (- 0 (cdr xyPair)) (- 0 (car xyPair))) ) ;return (-y, -x)
          ((= octant 6) (cons (cdr xyPair) (- 0 (car xyPair))))        ;return (y, -x)
          ((= octant 7) (cons (car xyPair) (- 0 (cdr xyPair))))        ;return (x, -y)
)))

; Function that invokes the calculation of the points and corrects the output acording too the octant. It dos so by applying "SwitchOctant " to each element in the returned list
(define CorectOutput
  (lambda (start_x start_y end_x end_y octant)
    (map (lambda (xy) (switchOctant octant xy)) (calPoints start_x end_x start_y end_y (- end_x start_x) (- end_y start_y ) (- (- end_y start_y ) (- end_x start_x) )) )
    )
)

;Function for handling a horizontal and vertical lines
(define (HorizontalVerticalLine start_x start_y end_x end_y)
   (if (= start_x end_x)
       (if (< start_y end_y)
           (MakeVerticalLine start_y end_y start_x)
           (MakeVerticalLine end_y start_y start_x)
       )
       (if (< start_x end_x)
           (MakeHorizontalLine start_x end_x start_y)
           (MakeHorizontalLine end_x start_x start_y)
       )
  )
)

(define (MakeHorizontalLine small_x large_x const_y)
  (if (<= small_x large_x)
      (if (= small_x large_x) ;Check end condition
          (cons (cons large_x const_y) '()) ;Return end point
          (cons (cons small_x const_y) (MakeHorizontalLine (+ small_x 1) large_x const_y )) ;Recursive call
      )
  )
)

(define (MakeVerticalLine small_y large_y const_x)
  (if (<= small_y large_y)
      (if (= small_y large_y) ;Check end condition
          (cons (cons const_x large_y ) '()) ;Return end point
          (cons (cons const_x small_y ) (MakeVerticalLine (+ small_y 1) large_y const_x )) ;Recursive call
      )
  )
)




(define DetermineOctant
  (lambda (start_x start_y end_x end_y)


    ;Check the line is not horizontal or vertical
    (if (and (not (= start_x end_x)) (not(= start_y end_y)))
        ;Desition tree for determining witch octant the line is in.
        (if (> (- end_y start_y) 0) ;It is going up?
            (if (> (- end_x start_x) 0) ; Is it going right?
                (if (< (/ (- end_y start_y) (- end_x start_x)) 1) ; Is the slope below 1
                    (CorectOutput start_x start_y end_x end_y 0) ;OCT = 0 (x, y)
                    (CorectOutput start_y start_x end_y end_x 1) ;OCT = 1 (y, x)
                    )
                (if (< (/ (- end_y start_y) (- end_x start_x)) -1) ; Is the slope below -1
                    (CorectOutput start_y (- 0 start_x) end_y (- 0 end_x) 2) ;OCT = 2 (y, -x)   <-- Here it is going up (positive y) and going left (negative x) and with a slope below -1 it is in oct 2.
                    (CorectOutput (- 0 start_x) start_y (- 0 end_x) end_y 3) ;OCT = 3 (-x, y)
                    )) ;end going right
            (if (< (- end_x start_x) 0) ; Is it going left?
                (if (< (/ (- end_y start_y) (- end_x start_x)) 1) ; Is the slope below 1
                    (CorectOutput (- 0 start_x) (- 0 start_y) (- 0 end_x) (- 0 end_y) 4) ;OCT = 4 (-x, -y)
                    (CorectOutput (- 0 start_y) (- 0 start_x) (- 0 end_y) (- 0 end_x) 5) ;OCT = 5 (-y, -x)
                    )
                (if (< (/ (- end_y start_y) (- end_x start_x)) -1) ; Is the slope below -1
                    (CorectOutput (- 0 start_y) start_x (- 0 end_y) end_x 6) ;OCT = 6 (-y, x)
                    (CorectOutput start_x (- 0 start_y) end_x (- 0 end_y )7) ;OCT = 7 (x, -y)
                    )) ;end going left
            );end going up
    (HorizontalVerticalLine start_x start_y end_x end_y) ;Make a horizontal or vartical line instead
    )
 )
)



(define (LINE start_x start_y end_x end_y)
    (DetermineOctant start_x
                     start_y
                     end_x
                     end_y)
    )



; == CIRCLE method ==
;; Calculates 8 coordinates for the circle slope (one of 8 octants)
;; Takes starting point x0 y0, the radius/x, y which is initially 0
(define xy_values
  (lambda (x0 y0 x y decision_over_2)
    (if(>= x y)
       (if(<= decision_over_2 0)
          (cons (cons x y) (xy_values x0 y0 x (+ y 1) (+ decision_over_2 (+ (* y 2) 1))))
          (cons (cons x y) (xy_values x0 y0 (- x 1) (+ y 1) (+ decision_over_2 (+ (* (- y (- x 1)) 2) 1)))))
       '())))

;; Calculates all coordinates for an octant by matching x_0 and y_0 with an operator and the calculated circle slope coordinates
;; Takes a operator (+ or -) and list operator (car or cdr), choosing which of the axis values should be applied for x_0 and y_0
(define octant_offset
  (lambda (operator_x list_operator_x operator_y list_operator_y x_0 y_0 pair list)
    (if(null? pair)
         list
         (cons (cons (single_axis_coordinate operator_x list_operator_x (car pair) x_0) (single_axis_coordinate operator_y list_operator_y (car pair) y_0)) (octant_offset operator_x list_operator_x operator_y list_operator_y x_0 y_0 (cdr pair) list)))))

;; Calculates a single axis coordinate
;; Takes a operator (+ or -) , list operator (car or cdr)
(define single_axis_coordinate
  (lambda (operator list_operator pair single_axis_0)
    (+ (operator (list_operator pair)) single_axis_0)))

;; Calculates all octants
(define calc_octans
  (lambda (x_0 y_0 radius xy_values)
    (octant_offset  + car + cdr x_0 y_0 xy_values
                    (octant_offset  + cdr + car x_0 y_0 xy_values
                                    (octant_offset  - car + cdr x_0 y_0 xy_values
                                                    (octant_offset  - cdr + car x_0 y_0 xy_values
                                                                    (octant_offset  - car - cdr x_0 y_0 xy_values
                                                                                    (octant_offset  - cdr - car x_0 y_0 xy_values
                                                                                                    (octant_offset  + car - cdr x_0 y_0 xy_values
                                                                                                                    (octant_offset  + cdr - car x_0 y_0 xy_values (list)))))))))))

(define CircleInputs
  (lambda (center_coordinate radius)
    (calc_octans (car center_coordinate) (cdr center_coordinate) radius (xy_values (car center_coordinate) (cdr center_coordinate) radius 0 (- radius 1)))))

; Corecting the circle center with the ofsets
(define (CIRCLE center_coordinate radius)
    (CircleInputs (cons (car center_coordinate) (cdr center_coordinate)) radius)
)

; FILL FIGURE



(define (FILL FigurAsListOfPoints)
  (FindPixelsWithInFigur (FindCenterOfMassPoint FigurAsListOfPoints) FigurAsListOfPoints)
 )

(define (FindPixelsWithInFigur PointIndsideFigurGuess ListOfBoundPoints)
  (if (DosListContainPoint  ListOfBoundPoints PointIndsideFigurGuess)
      '()
      (cons PointIndsideFigurGuess (FindPixelsWithInFigur (AddPoints PointIndsideFigurGuess (cons 1 0)) ListOfBoundPoints))
      )
 )


(define (DosListContainPoint List Point)
  (if (null? List)                               ;End condition
      #f                                         ;If list is empty return false
      (if (equal? (car List) Point)              ;Is point and first list element the same?
          #t
          (DosListContainPoint (cdr List) Point) ;Call self with rest of list
          )
      )
)

(define (FindCenterOfMassPoint List)
  (CalSumOfPoints List (cons 0 0) 0)
)

; Sumpoint (cons 0 0); Counter 0; (Can we make defauld values?)
(define (CalSumOfPoints ListOfPoints SumPoint Counter)
  (if (null? ListOfPoints)
      (DevidePoint SumPoint Counter)
      ( CalSumOfPoints (cdr ListOfPoints) (AddPoints (car ListOfPoints) SumPoint) (+ Counter 1) )
  )
)

(define (AddPoints Point_1 Point_2)
  (cons (+ (car Point_1) (car Point_2)) (+ (cdr Point_1) (cdr Point_2)))
 )

(define (DevidePoint Point Devisor)
  (cons (floor (/ (car Point) Devisor)) (floor (/ (cdr Point) Devisor)))
)