package view.console;

import exceptions.cumplirEdadEx;
import exceptions.formatoDniEx;
import exceptions.formatoEmailEx;
import exceptions.formatoErrorEx;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import model.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inicialización de la escuela de manejo
        ArrayList<Teacher> teachers = new ArrayList<>();
        ArrayList<Employee> employees = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();
        DrivingSchool drivingSchool = new DrivingSchool("AutoSchool", "1234", "www.autoschool.com", teachers, employees, students);

        int option;

        do {
            displayMenu();
            option = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            switch (option) {
                case 1 -> {
                    try {
                        addStudent(scanner, drivingSchool);
                    } catch (cumplirEdadEx ex) {
                        System.out.println(ex.getMessage());
                    } catch (formatoDniEx ex) {
                        System.out.println(ex.getMessage());
                    } catch (formatoEmailEx ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                case 2 ->
                    addTeacher(scanner, drivingSchool);
                case 3 -> {
                    try {
                        addEmployee(scanner, drivingSchool);
                    } catch (formatoErrorEx ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                case 4 ->
                    removeStudentById(scanner, drivingSchool);
                case 5 ->
                    removeTeacher(scanner, drivingSchool);
                case 6 ->
                    removeEmployee(scanner, drivingSchool);
                case 7 ->
                    System.out.println(drivingSchool.listStudents());
                case 8 ->
                    System.out.println(drivingSchool.listTeacher());
                case 9 ->
                    System.out.println(drivingSchool.listEmployees());
                case 10 ->
                    System.out.println(drivingSchool.studentsWithPracticalPending());
                case 11 ->
                    calculateFinancials(drivingSchool);
                case 12 ->
                    evaluateStudent(scanner, drivingSchool);
                case 13 ->
                    System.out.println(drivingSchool.listApprovedStudents());
                case 14 ->
                    System.out.println(drivingSchool.listNotApprovedStudents());
                case 0 ->
                    System.out.println("Saliendo del sistema...");
                default ->
                    System.out.println("Opción inválida. Por favor, elige una opción válida.");
            }
        } while (option != 0);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1) Dar de alta un nuevo alumno");
        System.out.println("2) Dar de alta un nuevo profesor");
        System.out.println("3) Dar de alta un nuevo empleado");
        System.out.println("4) Dar de baja un alumno");
        System.out.println("5) Dar de baja un profesor");
        System.out.println("6) Dar de baja un empleado");
        System.out.println("7) Listar datos de alumnos");
        System.out.println("8) Listar datos de profesores");
        System.out.println("9) Listar datos de empleados");
        System.out.println("10) Listar datos de alumnos con pendiente el examen práctico");
        System.out.println("11) Calcular ingresos totales y gastos totales");
        System.out.println("12) Evaluar un alumno (aprobar teoría y/o práctica)");
        System.out.println("13) Listar alumnos aprobados");
        System.out.println("14) Listar alumnos no aprobados");
        System.out.println("0) Salir");
        System.out.print("Elige una opción: ");
    }

    private static void addStudent(Scanner scanner, DrivingSchool drivingSchool) throws cumplirEdadEx, formatoDniEx, formatoEmailEx {
        System.out.println("\n--- Alta de Alumno ---");
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Apellido: ");
        String surname = scanner.nextLine();
        System.out.print("ID: ");
        String id = scanner.nextLine();

        // Validar el formato del DNI
        if (!id.matches("\\d{8}[A-Za-z]")) {
            throw new formatoDniEx("El formato del DNI no es válido. Debe ser 8 dígitos seguidos de una letra.");
        }

        System.out.print("Email: ");
        String email = scanner.nextLine();

        // Validar el formato del email
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new formatoEmailEx("El formato del email no es válido.");
        }

        System.out.print("Fecha de nacimiento (DD-MM-AAAA): ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        // Validar edad mínima
        int age = Period.between(date, LocalDate.now()).getYears();
        if (age < 18) {
            throw new cumplirEdadEx("El alumno tiene que ser mayor de edad");
        }

        System.out.print("Estado de aprobación (0: no aprobado, 1: aprobado): ");
        int approvalStatus = scanner.nextInt();
        System.out.print("Cuota de inscripción: ");
        float registrationFee = scanner.nextFloat();
        scanner.nextLine(); // Consumir nueva línea

        // Mostrar profesores disponibles
        if (drivingSchool.getTeachersList().isEmpty()) {
            System.out.println("No hay profesores registrados. Por favor, añade un profesor antes de asignar alumnos.");
            return; // Salir del método si no hay profesores
        }

        System.out.println("\n--- Profesores Disponibles ---");
        for (int i = 0; i < drivingSchool.getTeachersList().size(); i++) {
            Teacher teacher = drivingSchool.getTeachersList().get(i);
            System.out.println(i + ") " + teacher.getName() + " " + teacher.getSurname());
        }

        System.out.print("Selecciona el índice del profesor para asignar al alumno: ");
        int teacherIndex = scanner.nextInt();
        scanner.nextLine(); // Consumir nueva línea

        // Validar selección del profesor
        if (teacherIndex < 0 || teacherIndex >= drivingSchool.getTeachersList().size()) {
            System.out.println("Índice de profesor no válido. Alumno no añadido.");
            return;
        }

        Teacher selectedTeacher = drivingSchool.getTeachersList().get(teacherIndex);

        // Crear nuevo alumno con profesor asignado
        Student newStudent = new Student(name, surname, id, email, date, approvalStatus, registrationFee, selectedTeacher);
        drivingSchool.addStudent(newStudent);
        System.out.println("Alumno añadido exitosamente y asignado al profesor: " + selectedTeacher.getName() + " " + selectedTeacher.getSurname());
    }

    private static void addTeacher(Scanner scanner, DrivingSchool drivingSchool) {
        System.out.println("\n--- Alta de Profesor ---");
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Apellido: ");
        String surname = scanner.nextLine();
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Fecha de nacimiento (DD-MM-AAAA): ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        System.out.print("Salario: ");
        float salary = scanner.nextFloat();
        scanner.nextLine();
        System.out.print("Puesto: ");
        String position = scanner.nextLine();

        Teacher newTeacher = new Teacher(name, surname, id, email, date, salary, position);
        drivingSchool.addTeacher(newTeacher);
        System.out.println("Profesor añadido exitosamente.");
    }

    private static void addEmployee(Scanner scanner, DrivingSchool drivingSchool) throws formatoErrorEx {
        System.out.println("\n--- Alta de Empleado ---");
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Apellido: ");
        String surname = scanner.nextLine();
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Fecha de nacimiento (DD-MM-AAAA): ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        System.out.print("Salario: ");
        float salary = scanner.nextFloat();
        if (salary < 100) {
            throw new formatoErrorEx("Salario no válido");
        }
        scanner.nextLine();
        System.out.print("Puesto: ");
        String position = scanner.nextLine();

        Employee newEmployee = new Employee(name, surname, id, email, date, salary, position);
        drivingSchool.addEmployee(newEmployee);
        System.out.println("Empleado añadido exitosamente.");
    }

    private static void removeStudentById(Scanner scanner, DrivingSchool drivingSchool) {
        System.out.println("\n--- Baja de Alumno ---");
        System.out.print("Introduce el ID del alumno a eliminar: ");
        String studentId = scanner.nextLine();
        Student studentToRemove = drivingSchool.getStudentsList().stream()
                .filter(student -> student.getId().equals(studentId))
                .findFirst()
                .orElse(null);

        if (studentToRemove != null) {
            drivingSchool.removeStudent(studentToRemove);
            System.out.println("Alumno eliminado exitosamente.");
        } else {
            System.out.println("No se encontró un alumno con el ID proporcionado.");
        }
    }

    private static void removeTeacher(Scanner scanner, DrivingSchool drivingSchool) {
        System.out.println("\n--- Baja de Profesor ---");
        System.out.print("Introduce el índice del profesor a eliminar: ");
        int teacherIndex = scanner.nextInt();
        if (teacherIndex >= 0 && teacherIndex < drivingSchool.getTeachersList().size()) {
            drivingSchool.removeTeacher(drivingSchool.getTeachersList().get(teacherIndex));
            System.out.println("Profesor eliminado.");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    private static void removeEmployee(Scanner scanner, DrivingSchool drivingSchool) {
        System.out.println("\n--- Baja de Empleado ---");
        System.out.print("Introduce el índice del empleado a eliminar: ");
        int employeeIndex = scanner.nextInt();
        if (employeeIndex >= 0 && employeeIndex < drivingSchool.getEmployeesList().size()) {
            drivingSchool.removeEmployee(drivingSchool.getEmployeesList().get(employeeIndex));
            System.out.println("Empleado eliminado.");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    private static void calculateFinancials(DrivingSchool drivingSchool) {
        double revenue = drivingSchool.calculateRevenue();
        double expenses = drivingSchool.calculateExpenses();
        System.out.println("Ingresos totales: " + revenue);
        System.out.println("Gastos totales: " + expenses);
        System.out.println("Balance: " + (revenue - expenses));
    }

    private static void evaluateStudent(Scanner scanner, DrivingSchool drivingSchool) {
        System.out.println("\n--- Evaluar Alumno ---");
        System.out.print("Introduce el ID del alumno a evaluar: ");
        String studentId = scanner.nextLine();
        System.out.print("Introduce el nuevo estado de aprobación (1: aprobado, 0: no aprobado): ");
        int approvalStatus = scanner.nextInt();
        scanner.nextLine();

        Student studentToEvaluate = drivingSchool.getStudentsList().stream()
                .filter(student -> student.getId().equals(studentId))
                .findFirst()
                .orElse(null);

        if (studentToEvaluate != null) {
            studentToEvaluate.setApprovalStatus(approvalStatus);
            System.out.println("Estado del alumno actualizado.");
        } else {
            System.out.println("No se encontró un alumno con el ID proporcionado.");
        }
    }
}
