package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StringCalculatorTests {

    StringCalculator stringCalculator;

    @BeforeEach
    void setup() {
        stringCalculator = new StringCalculator();
    }

    @DisplayName("빈 문자열을 입력받았을 때 0(int)을 출력")
    @ParameterizedTest
    @ValueSource(strings = {""})
    void inputEmptyString(String input) {
        int result = stringCalculator.add(input);
        assertThat(result).isEqualTo(0);
    }

    @DisplayName("null을 입력받았을 때 0(int)을 출력")
    @Test
    void nullCheck() {
        int result = stringCalculator.add(null);
        assertThat(result).isEqualTo(0);
    }

    @DisplayName("정상적인 문자열이 입력됐을 때 추출된 숫자 콜렉션의 총합을 계산")
    @ParameterizedTest
    @ValueSource(strings = {"112:3:5,523:123,1", "112:3:5:523:123:1", "112,3,5,523,123,1"})
    void addHappyPath(String input) {
        int result = stringCalculator.add(input);
        assertThat(result).isEqualTo(767);
    }

    @DisplayName("입력값 중 기본 구분자(:와 쉼표)를 제외한 숫자만 골라낸 콜렉션 출력")
    @ParameterizedTest
    @ValueSource(strings = {"112:3:5,523:123,1", "112:3:5:523:123:1", "112,3,5,523,123,1"})
    void compositeParser(String input) {
        List<Integer> numbers = stringCalculator.extractNumbers(input);
        assertThat(numbers.get(0)).isEqualTo(112);
        assertThat(numbers.get(3)).isEqualTo(523);
    }

    @DisplayName("커스텀 구분자가 있는 경우 커스텀 구분자 반환")
    @ParameterizedTest
    @ValueSource(strings = {"//!\n112!3:5,523:123!1"})
    void getCustomDivider(String input) {
        String customDivider = stringCalculator.getCustomDivider(input);
        assertThat(customDivider).isEqualTo("!");
    }

    @DisplayName("커스텀 구분자가 있는 경우 숫자 콜렉션 추출")
    @ParameterizedTest
    @ValueSource(strings = {"//!\n112!3:5,523:123!1"})
    void getNumbersWhenHasCustomDivider(String input) {
        List<Integer> numbers = stringCalculator.extractNumbers(input);
        assertThat(numbers.get(0)).isEqualTo(112);
        assertThat(numbers.get(3)).isEqualTo(523);
    }

    @DisplayName("커스텀 구분자가 포함된 정상적인 문자열이 입력됐을 때 추출된 숫자 콜렉션의 총합을 계산")
    @ParameterizedTest
    @ValueSource(strings = {"112:3:5,523:123,1", "112:3:5:523:123:1", "112,3,5,523,123,1", "//!\n112!3:5,523:123!1"})
    void addManyCases(String input) {
        int result = stringCalculator.add(input);
        assertThat(result).isEqualTo(767);
    }

    @DisplayName("음수가 섞인 경우 RuntimeException 발생")
    @ParameterizedTest
    @ValueSource(strings = {"-112:3:5,523:123,1"})
    void errorOccurWhenContainsMinus(String input) {
        assertThatThrownBy(() -> stringCalculator.add(input)).isInstanceOf(RuntimeException.class);
    }

    @DisplayName("숫자가 아닌 값이 섞인 경우 RuntimeException 발생")
    @ParameterizedTest
    @ValueSource(strings = {"112:hello:5,523:123,1"})
    void errorOccurWhenContainsNotNumber(String input) {
        assertThatThrownBy(() -> stringCalculator.add(input)).isInstanceOf(RuntimeException.class);
    }
}
