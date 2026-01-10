export class ProductoFechaModificacion {
    value: Date;

    constructor(value: Date) {
        this.value = value;

        this.checkValue();
    }

    private checkValue() {

    }

    static now(): ProductoFechaModificacion {
        return new ProductoFechaModificacion(new Date());
    }
}