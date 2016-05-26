package lf1.plp.expressions2.memory;

import java.util.Map;
import java.util.Stack;

import lf1.plp.expressions2.expression.Id;

/**
 * @author bldb, efas, jcbr, srmq
 *
 * This class performs some utility operations over a Stack
 */
public class StackHandler {
	
	private StackHandler() {
	}
	
	
	/**
	 * Looks for the object whose Id is 'id' on the mappings that exist on the 
	 * specified Stack.
	 * @param stack, the Stack which contains the mappings (Id->Object)
	 * @param id, the Id of the desired object.
	 * @return an <code>Object</code> indexed by the Id
	 * @throws IdentificadorNaoDeclaradoException if there is not any object indexed by 'id'
	 */
	public static Object getFromId (Stack stack, Id id) throws IdentificadorNaoDeclaradoException {
		Object result = null;
		Stack auxStack = new Stack();
		while (result == null && !stack.empty()) {
			Map aux = (Map) stack.pop();
			auxStack.push(aux);
			result = aux.get(id);
		}
		while (!auxStack.empty()) {
			stack.push(auxStack.pop());
		}
		if (result == null) {
			throw new IdentificadorNaoDeclaradoException();
		} 
		
		return result;
	}

			
	/**
	 * Adds a mapping on the specified Stack
	 * @param stack, the Stack which contains the mappings
	 * @param id, the Id that will index the new object
	 * @param object, the content that will be indexed
	 * @throws VariavelJaDeclaradaException, if 'id' is already used in an existing
	 * mapping
	 */
	public static void mapIdObject (Stack stack, Id id, Object object) throws IdentificadorJaDeclaradoException {
		Map aux = (Map) stack.peek();
    	if (aux.put(id, object) != null) {
    		throw new IdentificadorJaDeclaradoException();
    	}
	
	}

}
