export class ProveedorId {
    value: number;

    constructor(value: number) {
        this.value = value;

        this.checkValue();
    }

    private checkValue() {
        if(this.value < 0) {
            throw new Error('Proveedor id no puede ser menor a 0.');
        }
    }
}