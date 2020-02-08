# 키친포스

## 요구 사항
- 목표: 유지 보수가 용이한 POS 시스템을 구현해 위기에 빠진 사장님을 돕는다.

- 주문(Orders)
    - [ ] 주문 테이블, 주문 아이템으로 주문을 생성 할 수 있다.
    - [ ] 주문 아이템은 메뉴, 메뉴 수량으로 구성된다.
    - [ ] 주문 시 주문 아이템은 하나 이상 있어야만 한다.
    - [ ] 존재하지 않는 메뉴는 주문 할 수 없다.
    - [ ] 주문 테이블이 비어 있는 상태에서는 주문 할 수 없다.
    - [ ] 주문 목록을 조회 할 수 있다.
    - [ ] 주문의 상태를 변경 할 수 있다.
    - [ ] 주문 상태는 '조리중, 식사중, 완료'로 나뉜다.
    - [ ] 완료 상태의 주문은 상태를 변경 할 수 없다.
    
- 상품(Products)
    - [ ] 상품명, 가격으로 새로운 상품을 생성할 수 있다.
    - [ ] 상품 목록을 조회할 수 있다.
    - [ ] 상품 생성 시 상품 가격은 반드시 있어야 한다.
    - [ ] 상품 생성 시 상품 가격은 0보다 커야 한다.

- 테이블 그룹(TableGroups)
    - [ ] 복수의 테이블로 테이블 그룹을 생성할 수 있다.
    - [ ] 테이블이 1개 이하면 테이블 그룹을 생성할 수 없다.
    - [ ] 존재하지 않는 테이블로 테이블 그룹을 생성할 수 없다.
    - [ ] 생성된 테이블 그룹을 삭제할 수 있다.
    - [ ] 상태가 '삭사중', '조리중'인 주문 테이블이 속한 테이블 그룹은 삭제할 수 없다.

- 테이블(Table)
    - [ ] 테이블 목록을 조회할 수 있다.
    - [ ] 테이블에서 어떤 테이블 그룹에 속해 있는지, 손님이 몇명이 있는지, 비어 있는지에 대한 정보가 담겨 있다.
    - [ ] 테이블의 공석 여부를 변경할 수 있다.
    - [ ] 테이블 그룹에 속한 테이블의 공석 여부를 변경할 수 없다.
    - [ ] 식사중이거나 조리중인 테이블의 공석 여부를 변경할 수 없다.
    - [ ] 테이블에 앉아 있는 손님의 수를 변경할 수 있다.
    - [ ] 테이블의 손님 수를 음수로 변경할 수 없다.
    - [ ] 비어있는 테이블의 손님 수를 변경할 수 없다.
    
- 메뉴그룹(MenuGroups)
    - [ ] 메뉴 그룹 이름을 전달해서 새로운 메뉴 그룹을 만들 수 있다.
    - [ ] 메뉴 그룹 전체를 조회할 수 있다.
    
- 메뉴(Menus)
    - [ ] 메뉴 전체를 조회 할 수 있다.
    - [ ] 이름, 그룹, 메뉴그룹, 상품 정보를 전달해서 새로운 메뉴를 생성 할 수 있다.
    - [ ] 가격 없이 메뉴를 생성 할 수 없다.
    - [ ] 가격이 0원 이하인 메뉴를 생성 할 수 없다.
    - [ ] 존재하지 않는 메뉴그룹에 메뉴를 생성할 수 없다.

## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |

## 모델링
