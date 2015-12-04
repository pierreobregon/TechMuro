package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.VariablesGenerales;

public interface VariableManager extends Service<VariablesGenerales> {

	public List<VariablesGenerales> tipoClienteList();

}
