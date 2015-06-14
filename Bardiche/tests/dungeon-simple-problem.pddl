(define (problem dungeon)
  (:domain dungeon)
  (:objects Mulak Hans Balou sprinkhaan1 - personage
            startkamer - kamer
            staf klauwen - voorwerp)
  (:protagonist Mulak)
  (:init    (in Mulak startkamer)
            (heeft Mulak staf)
            (wapen staf)
            (in Hans startkamer)
            (in Balou startkamer)
            (in sprinkhaan1 startkamer)
            (heeft sprinkhaan1 klauwen)
            (wapen klauwen)
            (intends sprinkhaan1 (dood Hans))
            ;;(intends sprinkhaan1 (dood Balou))
  )
            
  (:bardichegoal 
    (good 
        (and (not (dood Hans)) (dood sprinkhaan1))
        (and (not (dood Balou)) (dood sprinkhaan1))
    )
    (bad (dood Mulak))
  )
)