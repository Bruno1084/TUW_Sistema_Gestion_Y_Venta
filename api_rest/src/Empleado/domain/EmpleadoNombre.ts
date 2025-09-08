export class EmpleadoNombre {
    value: string;

    constructor(value: string) {
        this.value = value;
        this.checkValue();
    }

    private checkValue() {
        // If value name contains any number.
        if(/\d/.test(this.value)) {
            throw new Error('Nombre de empleado inválido')
        }
    }
}