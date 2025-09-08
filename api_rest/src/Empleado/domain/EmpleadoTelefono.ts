export class EmpleadoTelefono {
    value: string;

    constructor(value: string) {
        this.value = value;
        this.checkValue();
    }

    private checkValue() {
        if(/[a-zA-Z]/.test(this.value)) {
            throw new Error('Teléfono empleado no puede tener caracteres');
        }
    }

}