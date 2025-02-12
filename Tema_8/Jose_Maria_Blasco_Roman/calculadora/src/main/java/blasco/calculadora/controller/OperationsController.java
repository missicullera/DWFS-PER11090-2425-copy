package blasco.calculadora.controller;

import blasco.calculadora.data.model.CalculatorOperation;
import blasco.calculadora.controller.model.OperationDto;
import blasco.calculadora.service.OperationsService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operations")
public class OperationsController {

    private final OperationsService operationsService;

    public OperationsController(OperationsService operationsService) {
        this.operationsService = operationsService;
    }
    @PostMapping("/add")
    @Operation(
            operationId = "addNumbers",
            summary = "Sumar números",
            description = "Suma los números proporcionados separados por coma.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de los números a sumar.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OperationDto.class))
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "Operación realizada con éxito.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Operation.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Error en los datos proporcionados para la operación."
    )
    public ResponseEntity<Operation> addNumbers(@RequestBody OperationDto request) {
        Operation operation = operationsService.addNumbers(request.getNumbers());
        return ResponseEntity.ok(operation);
    }


    @PostMapping("/subtract")
    public ResponseEntity<Operation> subtractNumbers(@RequestBody OperationDto request) {
        Operation operation = operationsService.subtractNumbers(request.getNumbers());
        return ResponseEntity.ok(operation);
    }

    @PostMapping("/multiply")
    public ResponseEntity<Operation> multiplyNumbers(@RequestBody OperationDto request) {
        Operation operation = operationsService.multiplyNumbers(request.getNumbers());
        return ResponseEntity.ok(operation);
    }

    @PostMapping("/divide")
    public ResponseEntity<Operation> divideNumbers(@RequestBody OperationDto request) {
        try {
            Operation operation = operationsService.divideNumbers(request.getNumbers());
            return ResponseEntity.ok(operation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/root")
    public ResponseEntity<Operation> calculateRoot(@RequestBody OperationDto request) {
        Operation operation = operationsService.calculateRoot(request.getNumbers(), request.getIndice());
        return ResponseEntity.ok(operation);
    }

    @PostMapping("/power")
    public ResponseEntity<Operation> calculatePower(@RequestBody OperationDto request) {
        Operation operation = operationsService.calculatePower(request.getNumbers(), request.getExponente());
        return ResponseEntity.ok(operation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operation> getOperationById(@PathVariable Long id) {
        Operation operation = operationsService.getOperationById(id);
        if (operation != null) {
            return ResponseEntity.ok(operation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

