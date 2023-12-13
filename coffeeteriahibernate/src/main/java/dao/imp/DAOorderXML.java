package dao.imp;

import dao.ConstantsDAO;
import io.vavr.control.Either;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.log4j.Log4j2;

import model.Order;
import model.OrderItem;
import model.errors.ErrorCOrder;
import model.xml.OrderItemXML;
import model.xml.OrderXML;
import model.xml.OrdersXML;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DAOorderXML {

    public Either<ErrorCOrder, Integer> saveOrderToXML(List<Order> orderList) {
        Either<ErrorCOrder, Integer> res;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(OrdersXML.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            OrdersXML orderXML = new OrdersXML(parseOrdertoXML(orderList));
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Path xmlFile = Paths.get(ConstantsDAO.ROOT + orderList.get(0).getIdCo() + ConstantsDAO.XML);
            try (OutputStream outputStream = Files.newOutputStream(xmlFile, StandardOpenOption.CREATE)) {
                marshaller.marshal(orderXML, outputStream);
                res = Either.right(orderXML.getOrderXMLList().size());
            } catch (IOException e) {
                res = Either.left(new ErrorCOrder("Error writing XML file", 0));
            }
            res = Either.right(1);
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrder("There was an error writing the XML file", 0));
        }
        return res;
    }

    private List<OrderXML> parseOrdertoXML(List<Order> orderList) {
        List<OrderXML> orderXMLList = new ArrayList<>();
        for (Order order : orderList) {
            OrderXML orderXML = new OrderXML(order.getIdOrd(), parseOrderItemsToXML(order.getOrderItems()));
            orderXMLList.add(orderXML);
        }
        return orderXMLList;
    }

    private List<OrderItemXML> parseOrderItemsToXML(List<OrderItem> orderItems) {
        List<OrderItemXML> orderItemXMLList = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItemXML orderItemXML = new OrderItemXML(orderItem.getMenuItemObject().getNameMItem(), orderItem.getQuantity());
            orderItemXMLList.add(orderItemXML);
        }
        return orderItemXMLList;
    }
}


