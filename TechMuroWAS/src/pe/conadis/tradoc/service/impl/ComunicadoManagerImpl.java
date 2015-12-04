package pe.conadis.tradoc.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.sun.org.apache.xml.internal.security.utils.Base64;

import pe.conadis.tradoc.dao.ComunicadoDAO;
import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.entity.Comunicado;
import pe.conadis.tradoc.service.ComunicadoManager;
import pe.conadis.tradoc.service.VariableManager;
import pe.conadis.tradoc.util.Constants;


@Service
public class ComunicadoManagerImpl extends ServiceImpl<Comunicado> implements ComunicadoManager{

	@Autowired
	private ComunicadoDAO comunicadoDAO;
	
	@Autowired
	private VariableManager variableManager;
	
	@Override
	protected Dao<Comunicado> getDAO() {	
		return comunicadoDAO;
	}
	
	@Override
	@Transactional
	public byte[] byId(Integer id) throws Exception{
		
		String filename = comunicadoDAO.findById(id).getUrl().substring(14);
		String ruta = variableManager.finById(Constants.DIRECCIONIMGCOMUNICADOS).getValor();

		BufferedImage image = ImageIO.read(new File(ruta+filename));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		//byte[] res=baos.toByteArray();
		//String encodedImage = Base64.encode(baos.toByteArray());
		//byte[] t = Base64.decode(encodedImage);
		return baos.toByteArray();
		
	}

}
