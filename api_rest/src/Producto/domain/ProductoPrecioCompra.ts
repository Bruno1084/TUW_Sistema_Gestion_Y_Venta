export class ProductoPrecioCompra {
    value: number;

    constructor(value: number) {
        this.value = value;

        this.checkValue();
    }

    private checkValue() {
        if (this.value <= 0) {
            throw new Error('Producto precio compra no puede ser menor a cero.')
        }
    }

    static parsePrecio(value: string | null): number {
        if (!value) throw new Error("Precio vacío");

        const cleaned = value
            .replace(/\$/g, '')
            .replace(/\./g, '')
            .replace(',', '.')
            .trim();

        const number = Number(cleaned);

        if (isNaN(number)) throw new Error("Precio inválido");

        return number;
    }

}