export class ProductoFechaCreacion {
    value: Date;

    constructor(value: Date) {
        this.value = value;

        this.checkValue();
    }

    private checkValue() {

    }

    static now(): ProductoFechaCreacion {
        return new ProductoFechaCreacion(new Date());
    }
}