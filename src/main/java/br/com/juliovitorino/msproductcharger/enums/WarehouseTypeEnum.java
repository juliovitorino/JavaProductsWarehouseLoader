package br.com.juliovitorino.msproductcharger.enums;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Enumeration for type field into Warehouse instances
 *
 * @author Julio Vitorino
 * @since Apr 19, 2020
 */

public enum WarehouseTypeEnum {
    ECOMMERCE,
    PHYSICAL_STORE,
    UNDEFINED
}
