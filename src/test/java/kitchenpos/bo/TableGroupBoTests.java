package kitchenpos.bo;

import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.dao.TableGroupDao;
import kitchenpos.model.OrderTable;
import kitchenpos.model.TableGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TableGroupBoTests {

    @Mock
    private OrderDao orderDao;
    @Mock
    private OrderTableDao orderTableDao;
    @Mock
    private TableGroupDao tableGroupDao;

    @InjectMocks
    private TableGroupBo tableGroupBo;

    private TableGroup mockTableGroup = new TableGroup();
    private OrderTable mockOnePeopleOrderTable = new OrderTable();
    private OrderTable mockTwoPeopleOrderTable = new OrderTable();

    private List<OrderTable> mockOrderTables = new ArrayList<>();

    @BeforeEach
    public void setup() {
        setupTableGroup();
        setupOrderTables();

        mockOrderTables.add(mockOnePeopleOrderTable);
        mockOrderTables.add(mockTwoPeopleOrderTable);
    }

    @DisplayName("존재하는 두 개 이상의 주문 테이블로 테이블 그룹 생성 성공")
    @Test
    public void createTableGroupSuccess() {
        given(orderTableDao.findAllByIdIn(Arrays.asList(1L, 2L))).willReturn(mockOrderTables);
        given(tableGroupDao.save(mockTableGroup)).willReturn(mockTableGroup);

        TableGroup tableGroup = tableGroupBo.create(mockTableGroup);

        assertThat(tableGroup.getCreatedDate().getDayOfMonth()).isEqualTo(LocalDateTime.now().getDayOfMonth());
        assertThat(tableGroup.getOrderTables().get(0).getTableGroupId()).isEqualTo(1L);
        assertThat(tableGroup.getOrderTables().get(0).isEmpty()).isEqualTo(false);
    }

    @DisplayName("한 개 이하의 주문 테이블로 테이블 그룹 생성 시도 시 실패")
    @ParameterizedTest
    @MethodSource("provideInvalidOrderTables")
    public void createTableGroupFailWithInvalidOrderTables(List<OrderTable> orderTables) {
        mockTableGroup.setOrderTables(orderTables);

        assertThatThrownBy(() -> tableGroupBo.create(mockTableGroup)).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<List<OrderTable>> provideInvalidOrderTables() {
        return Stream.of(
                new ArrayList<>(),
                Collections.singletonList(new OrderTable())
        );
    }

    @DisplayName("존재하지 않는 주문 테이블로 테이블 그룹 생성 시도 시 실패")
    @Test
    public void createTableGroupFailWithNotExistOrderTable() {
        given(orderTableDao.findAllByIdIn(Arrays.asList(1L, 2L))).willReturn(new ArrayList<>());

        assertThatThrownBy(() -> tableGroupBo.create(mockTableGroup)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("비어있지 않은 주문 테이블로 테이블 그룹 생성 시도 시 실패")
    @Test
    public void createTableGroupFailWithNotEmptyOrderTable() {
        mockOnePeopleOrderTable.setEmpty(false);
        given(orderTableDao.findAllByIdIn(Arrays.asList(1L, 2L))).willReturn(mockOrderTables);

        assertThatThrownBy(() -> tableGroupBo.create(mockTableGroup)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이미 테이블 그룹에 속한 주문 테이블로 테이블 그룹 생성 시도 시 실패")
    @Test
    public void createTableGroupFailWithAlreadyInTableGroup() {
        mockOnePeopleOrderTable.setTableGroupId(33L);
        given(orderTableDao.findAllByIdIn(Arrays.asList(1L, 2L))).willReturn(mockOrderTables);

        assertThatThrownBy(() -> tableGroupBo.create(mockTableGroup)).isInstanceOf(IllegalArgumentException.class);
    }

    private void setupTableGroup() {
        mockTableGroup.setId(1L);
        mockTableGroup.setOrderTables(mockOrderTables);
    }

    private void setupOrderTables() {
        mockOnePeopleOrderTable.setId(1L);
        mockOnePeopleOrderTable.setNumberOfGuests(1);
        mockOnePeopleOrderTable.setEmpty(true);

        mockTwoPeopleOrderTable.setId(2L);
        mockTwoPeopleOrderTable.setNumberOfGuests(2);
        mockTwoPeopleOrderTable.setEmpty(true);
    }
}
