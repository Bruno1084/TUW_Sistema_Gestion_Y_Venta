export class EmpleadoId {
    value: number;

    constructor(value: number) {
        this.value = value;
        this.checkValue();
    }

    private checkValue() {
        if(this.value < 0) {
            throw new Error('Id de Empleado inválido');
        }
    }
}