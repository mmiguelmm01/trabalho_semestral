import model.Paciente;
import model.Trabalhador;
import repository.ConsultaRepository;
import repository.PacienteRepository;
import repository.TrabalhadorRepository;
import repository.TriagemRepository;

import java.sql.SQLException;

/**
 * Teste manual só de LEITURA: corre depois de schema.sql + data.sql.
 */
public class TesteRepositorios {
    public static void main(String[] args) {
        try {
            PacienteRepository pacientes = new PacienteRepository();
            TrabalhadorRepository trabalhadores = new TrabalhadorRepository();

            System.out.println(" Trabalhadores (repara na subclasse de cada um)");
            for (Trabalhador t : trabalhadores.listarTodos()) {
                System.out.println(t);
            }

            System.out.println("Pacientes ");
            for (Paciente p : pacientes.listarTodos()) {
                System.out.println(p);
            }

            System.out.println("Pesquisa por nome 'Isabel'");
            pacientes.pesquisarPorNome("Isabel").forEach(System.out::println);

            System.out.println(" Paciente 3: triagens e consultas ");
            new TriagemRepository().listarPorPaciente(3).forEach(System.out::println);
            new ConsultaRepository().listarPorPaciente(3).forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
