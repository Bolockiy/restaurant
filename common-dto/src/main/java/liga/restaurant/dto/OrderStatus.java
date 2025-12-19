package liga.restaurant.dto;
/**
 * Статус заказа в системе.
 */
public enum OrderStatus {

    /** Заказ готовится. */
    COOKING,

    /** Произошла ошибка при обработке заказа. */
    FAILED,

    /** Заказ готов и ожидает выдачи. */
    READY
}