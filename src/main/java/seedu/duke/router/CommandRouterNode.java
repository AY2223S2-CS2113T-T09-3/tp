package seedu.duke.router;

import seedu.duke.command.factory.ExecutableCommandFactory;
import seedu.duke.parser.CommandTokens;

import java.util.ArrayList;

public class CommandRouterNode implements Routable {
    ArrayList<RouteDescription> children;

    public CommandRouterNode() {
        this.children = new ArrayList<>();
    }

    /**
     * Returns a <code>CommandRouterNode</code> with the specified route description added.
     * @param routeName the name of the route to match.
     * @param result the <code>Routable</code> to resolve to.
     * @return a copy of this <code>CommandRouterNode</code> to allow chaining.
     */
    public CommandRouterNode route(String routeName, Routable result) {
        this.children.add(new RouteDescription(routeName, result));
        return this;
    }

    /**
     * Returns the resolved <code>ExecutableCommandFactory</code> by the <code>Routable</code> object.
     * <code>resolve</code> will iterate through each <code>RouteDescription</code> child which matches the next token.
     * The <code>ExecutableCommandFactory</code> to be resolved will be returned.
     * Iteration occurs in the same order as the routes were added.
     *
     * @param commandTokens the command tokens to resolve.
     * @return the resolved command factory, or <code>null</code> if no child matches the token.
     */
    public ExecutableCommandFactory resolve(CommandTokens commandTokens) {
        for (RouteDescription child : this.children) {
            // Check if the next token is equal to the name of the child route
            if (child.getRouteName().equals(commandTokens.peek())) {
                // Advance to the next token
                commandTokens.advance();
                // Attempt to resolve the child
                ExecutableCommandFactory resolvedRoute = child.getRoutable().resolve(commandTokens);
                // If the child could not be resolved, rewind to the original token and try the next child
                if (resolvedRoute == null) {
                    commandTokens.rewind();
                    continue;
                }
                return resolvedRoute;
            }
        }
        return null;
    }
}
